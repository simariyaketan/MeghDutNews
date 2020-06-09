package com.meghdut.news.view

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.meghdut.news.R
import com.meghdut.news.databinding.ActVideoPlayerBinding
import com.meghdut.news.network.WebAPIClient

class ActVideoPlayer : AppCompatActivity() {

    private val TAG = "MediaPlayerActivity"
    var livePlayer: SimpleExoPlayer? = null
    var playWhenReady: Boolean = true
    var currentWindow: Int = 0
    var playbackPosition: Long = 0

    lateinit var actVideoPlayerBinding: ActVideoPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_video_player)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        actVideoPlayerBinding = DataBindingUtil.setContentView(this, R.layout.act_video_player)

        var video_url: String = WebAPIClient.websiteUrl + intent.getStringExtra("video_url")
        Log.d("video_url", "video_url = " + video_url)
        if ((Util.SDK_INT <= 23 || livePlayer == null)) {
            initializePlayer(video_url)
        }


        /*actVideoPlayerBinding.playerView.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if(actVideoPlayerBinding.imgVideoPlayIcon.visibility == View.VISIBLE){
                    actVideoPlayerBinding.imgVideoPlayIcon.visibility = View.GONE
                }else{
                    actVideoPlayerBinding.imgVideoPlayIcon.visibility = View.VISIBLE
                    Handler().postDelayed({
                        actVideoPlayerBinding.imgVideoPlayIcon.visibility = View.GONE
                    },1000)
                }
                return false
            }

        })*/
    }

    override fun onResume() {
        super.onResume()
        // releasePlayer()
    }

    fun initializePlayer(video_url: String) {

        if (livePlayer == null) {
            livePlayer = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(this),
                DefaultTrackSelector(),
                DefaultLoadControl()
            )
            actVideoPlayerBinding.playerView.player = livePlayer
            livePlayer?.setPlayWhenReady(true)
            livePlayer?.seekTo(currentWindow, playbackPosition)
        }
        val mediaSource =
            buildMediaSource(Uri.parse(video_url))
        livePlayer?.prepare(mediaSource, true, false)
        actVideoPlayerBinding.playerView.useController = true
        val loopingMediaSource: LoopingMediaSource = LoopingMediaSource(mediaSource)
        livePlayer?.addListener(object : ExoPlayer.EventListener {
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
                Log.d(
                    TAG,
                    "onPlaybackParametersChanged..."
                )
            }

            override fun onSeekProcessed() {
                Log.d(
                    TAG,
                    "onSeekProcessed..."
                )
            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray?,
                trackSelections: TrackSelectionArray?
            ) {
                Log.d(
                    TAG,
                    "onTracksChanged..."
                )
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
                Log.d(
                    TAG,
                    "Listener-onPlayerError..." + error?.message
                )
                livePlayer?.stop()
                livePlayer?.prepare(loopingMediaSource)
                livePlayer?.playWhenReady = true
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                Log.d(
                    TAG,
                    "onLoadingChanged..."
                )
            }

            override fun onPositionDiscontinuity(reason: Int) {
                Log.d(
                    TAG,
                    "onPositionDiscontinuity..."
                )
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                Log.d(
                    TAG,
                    "onRepeatModeChanged..."
                )
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                Log.d(
                    TAG,
                    "onShuffleModeEnabledChanged..."
                )
            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
                Log.d(
                    TAG,
                    "onTimelineChanged..."
                )
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                Log.d(
                    TAG,
                    "onPlayerStateChanged..." + playbackState
                )

                if (playbackState == ExoPlayer.STATE_BUFFERING) {
                    actVideoPlayerBinding.progressWheel.visibility = View.GONE

                } else if (playbackState == ExoPlayer.STATE_IDLE) {
                    actVideoPlayerBinding.progressWheel.visibility = View.VISIBLE
                } else if (playbackState == ExoPlayer.STATE_READY) {
                    actVideoPlayerBinding.progressWheel.visibility = View.GONE
                } else if (playbackState == ExoPlayer.STATE_ENDED) {
                    actVideoPlayerBinding.progressWheel.visibility = View.GONE
                } else if (playbackState != ExoPlayer.STATE_BUFFERING &&
                    playbackState != ExoPlayer.STATE_IDLE && playbackState
                    != ExoPlayer.STATE_READY && playbackState
                    != ExoPlayer.STATE_ENDED
                ) {
                    actVideoPlayerBinding.progressWheel.visibility = View.GONE
                }
            }

        })
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val bandwidthMeter = DefaultBandwidthMeter()
        val userAgent = "exoplayer-codelab"

        if (uri.getLastPathSegment()!!.contains("mp3") || uri.getLastPathSegment()!!.contains("mp4")) {
            return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else if (uri.getLastPathSegment()!!.contains("m3u8")) {
            return HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else {
            val dashChunkSourceFactory = DefaultDashChunkSource.Factory(
                DefaultHttpDataSourceFactory("ua", bandwidthMeter)
            )
            val manifestDataSourceFactory = DefaultHttpDataSourceFactory(userAgent)
            return DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory)
                .createMediaSource(uri)
        }
    }

    private fun releasePlayer() {
        if (livePlayer != null) {
            playbackPosition = livePlayer!!.getCurrentPosition()
            currentWindow = livePlayer!!.getCurrentWindowIndex()
            playWhenReady = livePlayer!!.getPlayWhenReady()
            livePlayer?.release()
            livePlayer = null
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (livePlayer != null) livePlayer!!.stop()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (livePlayer != null) livePlayer!!.stop()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            livePlayer!!.release()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            livePlayer!!.release()
        }
    }
}

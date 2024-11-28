package com.gundogar.learnconnect


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gundogar.learnconnect.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNav: BottomNavigationView = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            if (destination.id == R.id.exoplayerFragment) {
             binding.bottomNavigation.hide()
            } else {
                binding.bottomNavigation.show()
            }
        }




    }

//    private fun initializePlayer() {
//        player = ExoPlayer.Builder(this).build()
//        binding.playerView.player = player
//
//        // Daha küçük bir test videosu kullanalım
//
//        // Media item oluştururken daha fazla kontrol ekleyelim
//        val mediaItem = MediaItem.Builder()
//            .setUri(videoUri)
//            .setMimeType(MimeTypes.APPLICATION_MP4) // MIME type belirtelim
//            .build()
//
//        player.apply {
//            setMediaItem(mediaItem)
//            // Hata dinleyicisi ekleyelim
//            addListener(object : Player.Listener {
//                override fun onPlayerError(error: PlaybackException) {
//                    // Hata durumunda log bastıralım
//                    Log.e("ExoPlayer Error", "Error: ${error.message}")
//                    error.printStackTrace()
//                }
//
//                override fun onPlaybackStateChanged(playbackState: Int) {
//                    when (playbackState) {
//                        Player.STATE_IDLE -> Log.d("ExoPlayer State", "Idle")
//                        Player.STATE_BUFFERING -> Log.d("ExoPlayer State", "Buffering")
//                        Player.STATE_READY -> Log.d("ExoPlayer State", "Ready")
//                        Player.STATE_ENDED -> Log.d("ExoPlayer State", "Ended")
//                    }
//                }
//            })
//
//            // Hazırlık ve oynatma
//            prepare()
//            playWhenReady = true
//        }
//    }

//    override fun onStart() {
//        super.onStart()
//        player.playWhenReady = true
//    }
//
//    override fun onResume() {
//        super.onResume()
//        player.play()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        player.pause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        player.playWhenReady = false
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        player.release()
//    }
}
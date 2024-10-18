    package com.example.yemektarifuygulamasi

    import android.annotation.SuppressLint
    import android.os.Bundle
    import android.util.Log
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.*
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.NavController
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.currentBackStackEntryAsState
    import androidx.navigation.compose.rememberNavController
    import androidx.navigation.navArgument
    import com.example.yemektarifuygulamasi.data.remote.localdtb.LocalViewModel
    import com.example.yemektarifuygulamasi.prensetation.randomrecipe.RandomRecipesViewModel
    import com.example.yemektarifuygulamasi.prensetation.randomrecipe.views.RecipesScreen
    import com.example.yemektarifuygulamasi.prensetation.CreateAccount.AuthenticationScreen
    import com.example.yemektarifuygulamasi.prensetation.detailrecipe.DetailScreen
    import com.example.yemektarifuygulamasi.prensetation.detailrecipe.DetailScreenViewModel
    import com.example.yemektarifuygulamasi.prensetation.searchrecipe.SearchRecipesViewModel
    import com.example.yemektarifuygulamasi.prensetation.searchrecipe.SearchScreen
    import com.example.yemektarifuygulamasi.presentation.favorites.FavoriteRecipesScreen
    import com.example.yemektarifuygulamasi.ui.theme.YemekTarifUygulamasiTheme
    import com.google.firebase.auth.FirebaseAuth
    import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
    class MainActivity : ComponentActivity() {
        private lateinit var auth: FirebaseAuth
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            auth = FirebaseAuth.getInstance()

            enableEdgeToEdge()
            setContent {
                YemekTarifUygulamasiTheme {
                  Screen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @Composable
    fun Screen() {
        val viewModel: RandomRecipesViewModel = viewModel()
        val detailViewModel : DetailScreenViewModel = viewModel()
        val searchviewModel : SearchRecipesViewModel = viewModel()
        val favoriteViewModel : LocalViewModel = viewModel()
        val navController = rememberNavController()
        val auth = FirebaseAuth.getInstance()

        Scaffold(
            bottomBar = {
                if (auth.currentUser != null) {
                    auth.currentUser?.email?.let { Log.e("e-posta", it) }
                    BottomNavigationBar(navController)
                }
            }
        ) { innerPadding ->
            NavHost(navController, startDestination = if (auth.currentUser == null) "AuthenticationScreen" else "RandomRecipes", Modifier.padding(innerPadding)) {
                composable(route = "AuthenticationScreen") {
                    AuthenticationScreen(navController)
                }

                composable(route = "RandomRecipes") {
                    RecipesScreen(Modifier, viewModel,navController,favoriteViewModel)
                }


                composable(route = "SearchRecipe") {
                   SearchScreen(viewModel = searchviewModel, navController = navController)
                }

                composable(route = "FavoritesScreen") {
                    FavoriteRecipesScreen(favoriteViewModel , navController)
                }

                composable(
                    route = "DetailScreen/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) {
                    val id = it.arguments?.getInt("id") // id'yi alıyoruz
                    if (id != null) {
                        DetailScreen(id = id , detailViewModel , favoriteViewModel)
                    }else{
                        DetailScreen(id = 5 , detailViewModel , favoriteViewModel)
                    }
                }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route


        NavigationBar {

            NavigationBarItem(
                icon = { Icon(painter = painterResource(R.drawable.baseline_home_24) , contentDescription = "recipes") },
                label = { Text(text = "Recipes") },
                selected = currentRoute == "RandomRecipes",
                onClick = { navController.navigate("RandomRecipes") }
            )

            NavigationBarItem(
                icon = { Icon(painter = painterResource(R.drawable.baseline_search_24) , contentDescription = "search") },
                label = { Text(text = "Search") },
                selected = currentRoute == "SearchRecipe",
                onClick = { navController.navigate("SearchRecipe") }
            )

            NavigationBarItem(
                icon = { Icon(painter = painterResource(R.drawable.baseline_favorite_24) , contentDescription = "") },
                label = { Text(text = "Favorites") },
                selected = currentRoute == "FavoritesScreen",
                onClick = { navController.navigate("FavoritesScreen") }
            )
        }
    }



    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        YemekTarifUygulamasiTheme {
            // Preview içeriği
        }
    }
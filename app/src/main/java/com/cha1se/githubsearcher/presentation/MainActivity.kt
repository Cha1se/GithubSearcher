package com.cha1se.githubsearcher.presentation

import Content
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.cha1se.githubsearcher.R
import com.cha1se.githubsearcher.di.dataModule
import com.cha1se.githubsearcher.di.domainModule
import com.cha1se.githubsearcher.di.presentationModule
import com.cha1se.githubsearcher.domain.models.Item
import com.cha1se.githubsearcher.presentation.ui.theme.Background
import com.cha1se.githubsearcher.presentation.ui.theme.DividerColor
import com.cha1se.githubsearcher.presentation.ui.theme.GithubSearcherTheme
import com.cha1se.githubsearcher.presentation.ui.theme.SecondTextColor
import com.cha1se.githubsearcher.presentation.ui.theme.TitleColor
import com.cha1se.githubsearcher.presentation.ui.theme.TopAppBarColor
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {

    val vm: MainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigation()
        }
    }

    @Composable
    fun MainScreen(navController: NavController) {
        GithubSearcherTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Background
            ) {
                Scaffold(
                    topBar = { Search() },
                    containerColor = Background
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Content(navController)
                    }
                }
            }
        }
    }

    @Composable
    fun AboutScreen(navController: NavController) {
        vm.getRepositoryContent()

        GithubSearcherTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Background
            ) {
                Scaffold(
                    topBar = { AboutTitle(navController) },
                    containerColor = Background
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        AboutContent()
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun AboutContent() {
        val item = vm.currentItem.asStateFlow().collectAsState().value
        val content = vm.contentFromRepo.asStateFlow().collectAsState().value

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(50.dp),
                            color = SecondTextColor
                        )
                        .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp),
                    text = if (item.private) "private" else "public",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = item.description,
                style = MaterialTheme.typography.bodyMedium
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp)
                    .nestedScroll(connection = object : NestedScrollConnection {}),
                horizontalArrangement = Arrangement.Start
            ) {
                items (item.topics) {
                    DescriptionTopicItem(title = it)
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .background(color = TopAppBarColor)
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DescriptionCounterItem(
                    icon = Icons.Rounded.Star,
                    item = item.stargazers_count.toString(),
                    "stars"
                )
                DescriptionCounterItem(
                    icon = Icons.Rounded.Info,
                    item = item.forks_count.toString(),
                    "forks"
                )
                DescriptionCounterItem(
                    icon = Icons.Rounded.Person,
                    item = item.watchers_count.toString(),
                    "watching"
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .nestedScroll(connection = object : NestedScrollConnection {})
            ) {
                items (content) {
                    ContentItem(content = it)
                }
            }

        }
    }

    @Composable
    fun ContentItem(content: Content) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = if (content.type == "file") R.drawable.file_colored else R.drawable.folder_colored),
                contentDescription = "ico",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = content.name, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
            Box(
                contentAlignment = Alignment.CenterEnd, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = content.commit.message,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }

        }
    }

    @Composable
    fun DescriptionCounterItem(icon: ImageVector, item: String, title: String) {
        Row(
            modifier = Modifier
                .background(color = TopAppBarColor, shape = RoundedCornerShape(50.dp))
                .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 2.dp),
                        imageVector = icon,
                        tint = Color.White,
                        contentDescription = "Search",
                    )
                    Text(
                        modifier = Modifier.padding(),
                        text = item,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 2.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(),
                        text = title,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }

    @Composable
    fun DescriptionTopicItem(title: String) {
        Row(
            modifier = Modifier
                .padding(end = 8.dp)
                .background(color = TitleColor, shape = RoundedCornerShape(50.dp))
                .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
            )
        }
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {

            composable("home") {
                MainScreen(navController)
            }

            composable("about") {
                AboutScreen(navController)
            }

        }
    }

    @Composable
    fun Content(navController: NavController) {
        val items = vm.repoList.collectAsState().value
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) {
                Item(navController, it)
            }
        }
    }

    @Composable
    fun Item(
        navController: NavController,
        currentData: Item,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    vm.currentItem.value = currentData
                    navController.navigate("about")
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    model = currentData.owner.avatar_url,
                    contentScale = ContentScale.Fit,
                    loading = { CircularProgressIndicator() },
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(10.dp))

                Column(

                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = currentData.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                    )
                    Text(
                        text = if (!currentData.description.isNullOrEmpty()) currentData.description else "",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = DividerColor
            )
        }
    }

    @Composable
    fun Search() {
        var query: String by rememberSaveable { mutableStateOf("") }
        var focusRequester = remember { FocusRequester() }
        TextField(
            modifier = Modifier
                .height(60.dp)
                .background(color = TopAppBarColor)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = query,
            textStyle = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            onValueChange = {
                query = it
                vm.updateDataFromQuery(query)
            },
            singleLine = true,
            colors = TextFieldDefaults
                .colors(
                    disabledContainerColor = TopAppBarColor,
                    unfocusedContainerColor = TopAppBarColor,
                    focusedContainerColor = TopAppBarColor,
                    focusedIndicatorColor = DividerColor,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = SecondTextColor,
                    unfocusedLeadingIconColor = SecondTextColor,
                    disabledLeadingIconColor = SecondTextColor
                ),
            placeholder = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(12.dp)
                        .wrapContentSize()
                        .clickable {
                            focusRequester.requestFocus()
                        },
                    imageVector = Icons.Default.Search,
                    tint = SecondTextColor,
                    contentDescription = "Search",
                )
            },
        )

    }

    @Composable
    fun AboutTitle(navController: NavController) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .background(color = TopAppBarColor)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(12.dp, end = 16.dp)
                    .wrapContentSize()
                    .clickable {
                        vm.loadRepositories()
                        navController.navigate("home")
                    },
                imageVector = Icons.Default.ArrowBack,
                tint = SecondTextColor,
                contentDescription = "Search",
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = SecondTextColor
            )
        }
    }
}


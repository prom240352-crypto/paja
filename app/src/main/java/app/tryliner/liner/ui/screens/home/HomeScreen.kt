package app.tryliner.liner.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToPlayer: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Quick actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onNavigateToSearch) {
                Text("Search")
            }
            Button(onClick = onNavigateToLibrary) {
                Text("Library")
            }
            Button(onClick = onNavigateToPlayer) {
                Text("Player")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Recent plays section
        Text(
            text = "Recently Played",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // TODO: Add recent plays list
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Daily mixes section
        Text(
            text = "Daily Mixes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // TODO: Add daily mixes
    }
}

package cba.ucb.com.broadcastyoursselft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import cba.ucb.com.broadcastyoursselft.models.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_posts.*

open class Posts : AppCompatActivity() {
    
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        posts = mutableListOf()
        adapter = PostAdapter(this, posts)
        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this)
        firestoreDb = FirebaseFirestore.getInstance()
        val postsReference = firestoreDb.collection("posts").limit(20).orderBy("creation_time_ms", Query.Direction.DESCENDING)
        postsReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()
            for (post in postList) {
                Log.i("POST", "post ${post}")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_profile) {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
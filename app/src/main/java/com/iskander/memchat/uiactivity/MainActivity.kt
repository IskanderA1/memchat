package com.iskander.memchat.uiactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.iskander.memchat.R
import com.iskander.memchat.cache.AccountCacheImpl
import com.iskander.memchat.cache.SharedPrefsManager
import com.iskander.memchat.data.account.AccountCache
import com.iskander.memchat.data.account.AccountRemote
import com.iskander.memchat.data.account.AccountRepositoryImpl
import com.iskander.memchat.domain.account.Register
import com.iskander.memchat.remote.account.AccountRemoteImpl
import com.iskander.memchat.remote.core.NetWorkHandler
import com.iskander.memchat.remote.core.Request
import com.iskander.memchat.remote.service.ServiceFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        val accountCache = AccountCacheImpl(SharedPrefsManager(sharedPreferences))
        val accountRemote = AccountRemoteImpl(Request(NetWorkHandler(this)),ServiceFactory.makeService(true))

        val accountRepository = AccountRepositoryImpl(accountRemote, accountCache)

        accountCache.saveToken("123")

        val register = Register(accountRepository)

        register(Register.Params("123@mail.com","abcd","123")){
            it.either({
                Toast.makeText(this, it.javaClass.simpleName, Toast.LENGTH_SHORT).show()
            },{
                Toast.makeText(this, "Аккаунт создан", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
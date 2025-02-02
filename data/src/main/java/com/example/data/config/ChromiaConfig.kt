package com.example.data.config

import android.content.Context
import net.postchain.client.config.PostchainClientConfig
import net.postchain.client.request.EndpointPool
import net.postchain.common.BlockchainRid
import java.util.Properties

class ChromiaConfig private constructor() {
    companion object {
        private const val CONFIG_FILE = "chromia_config.properties"
        private const val KEY_BLOCKCHAIN_RID = "blockchain.rid"
        private const val KEY_NODE_URL = "node.url"
        
        // Local development defaults - use 10.0.2.2 for Android emulator to access host machine
        private const val DEFAULT_NODE_URL = "http://10.0.2.2:7740"
        
        fun loadConfig(context: Context): PostchainClientConfig {
            val properties = Properties()
            try {
                context.assets.open(CONFIG_FILE).use { 
                    properties.load(it)
                }
            } catch (e: Exception) {
                throw IllegalStateException("Failed to load config file. Make sure chromia_config.properties exists in assets/", e)
            }
            
            val blockchainRid = properties.getProperty(KEY_BLOCKCHAIN_RID) 
                ?: throw IllegalStateException("blockchain.rid not found in config file")
            val nodeUrl = properties.getProperty(KEY_NODE_URL, DEFAULT_NODE_URL)
            
            return PostchainClientConfig(
                blockchainRid = BlockchainRid.buildFromHex(blockchainRid),
                endpointPool = EndpointPool.default(listOf(nodeUrl))
            )
        }
    }
} 
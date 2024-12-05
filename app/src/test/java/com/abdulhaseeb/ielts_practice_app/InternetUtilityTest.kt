package com.abdulhaseeb.ielts_practice_app

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.wolfbytetechnologies.ielts.Utils.InternetUtility
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InternetUtilityTest {

    private lateinit var internetUtility: InternetUtility
    private lateinit var mockContext: Context
    private lateinit var mockConnectivityManager: ConnectivityManager
    private lateinit var mockNetworkInfo: NetworkInfo

    @Before
    fun setup() {
        // Mock Context and ConnectivityManager
        mockContext = mockk()
        mockConnectivityManager = mockk()
        mockNetworkInfo = mockk()

        // Stubbing context to return mock connectivity manager
        every { mockContext.getSystemService(Context.CONNECTIVITY_SERVICE) } returns mockConnectivityManager
        internetUtility = InternetUtility(mockContext)
    }

    @Test
    fun `test isConnected returns true when network is connected`() {
        // Stubbing connected network
        every { mockConnectivityManager.activeNetworkInfo } returns mockNetworkInfo
        every { mockNetworkInfo.isConnected } returns true

        assertTrue(internetUtility.isConnected)
    }

    @Test
    fun `test isConnected returns false when network is not connected`() {
        // Stubbing disconnected network
        every { mockConnectivityManager.activeNetworkInfo } returns mockNetworkInfo
        every { mockNetworkInfo.isConnected } returns false

        assertFalse(internetUtility.isConnected)
    }

    @Test
    fun `test isConnected returns false when network info is null`() {
        // Stubbing null network info
        every { mockConnectivityManager.activeNetworkInfo } returns null

        assertFalse(internetUtility.isConnected)
    }
}

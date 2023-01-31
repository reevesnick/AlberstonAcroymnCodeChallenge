package com.nr.acronyminitalismcodechallenge

import com.nr.acronyminitalismcodechallenge.model.LFSModel
import com.nr.acronyminitalismcodechallenge.model.SfModel
import com.nr.acronyminitalismcodechallenge.model.VarsModel
import org.junit.Assert
import org.junit.Test

class ModelUnitTest {
    val varsTest = listOf(VarsModel("Hyprocloric Magna 1",1234,1914),(VarsModel("Hyprocloric Magna 2",1234,1914)))
    val lfsModelTest = listOf(LFSModel("Hyprocloric Magna 1", 1424,1924, varsTest))
    val sfModelTest = SfModel("rrm",lfsModelTest)

//    val lfsModelTestEmpty = emptyList()
    @Test
    fun `Format Test - String`(){
        Assert.assertEquals("Hyprocloric Magna 1",lfsModelTest[0].vars)
        Assert.assertNotEquals("1424",lfsModelTest[0].freq)
        Assert.assertNotEquals("1924",lfsModelTest[0].since)
    }

    @Test
    fun `Format Test - Integer`(){
        Assert.assertNotEquals("Hyprocloric Magna 1",lfsModelTest[0].vars)
        Assert.assertEquals("1424",lfsModelTest[0].freq)
        Assert.assertEquals("1924",lfsModelTest[0].since)
    }

    @Test
    fun `Empty Model Test` (){
//        Assert.assertEquals(listOf(),"")
    }




}
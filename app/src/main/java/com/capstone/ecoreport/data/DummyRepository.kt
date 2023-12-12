package com.capstone.ecoreport.data

import com.capstone.ecoreport.model.Dummy
import com.capstone.ecoreport.model.DummyData
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow

class DummyRepository {

    private val dummy = mutableListOf<Dummy>()

    init {
        if (dummy.isEmpty()) {
            DummyData.dummyList.forEach {
                dummy.add(it)
            }
        }
    }

    fun getDummyById(dummyId: Int): Dummy {
        return dummy.first {
            it.id == dummyId
        }
    }

    fun searchDummy(query: String) = flow {
        val data = dummy.filter {
            it.username.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    companion object {
        @Volatile
        private var INSTANCE: DummyRepository? = null

        fun getInstance(): DummyRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DummyRepository().also { INSTANCE = it }
            }
        }
    }
}
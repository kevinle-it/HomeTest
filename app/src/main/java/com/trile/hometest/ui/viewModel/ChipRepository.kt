package com.trile.hometest.ui.viewModel

import android.app.Application
import com.trile.hometest.store.api.ApiService
import com.trile.hometest.store.room.RoomDataSource
import com.trile.hometest.store.room.RoomHelper
import com.trile.hometest.store.room.entity.ChipEntity
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author lmtri
 * @since Aug 06, 2019 at 5:48 PM
 */
class ChipRepository(private val application: Application) {

    private val mDb = RoomDataSource.getInstance(application.applicationContext)
    private val mRoomHelper = RoomHelper(mDb)

    fun getChipListFromDB(): Maybe<List<ChipEntity>?> =
            Maybe.defer { Maybe.just(mRoomHelper.getAllChipItems()) }
                    .subscribeOn(Schedulers.io())
                    .onErrorResumeNext { _: Throwable ->
                        Maybe.just(ArrayList())
                    }

    fun getChipListFromAPI(): Maybe<List<ChipEntity>> =
            Maybe.just(ApiService.getInstance()?.getItemNames())
                    .subscribeOn(Schedulers.io())
                    .map {
                        val response = it.execute()
                        val nameList = response.body()
                        val chipItems = nameList?.map { name -> ChipEntity(name) } ?: ArrayList()
                        chipItems
                    }

    fun saveToDB(itemList: List<ChipEntity>?) =
            Single.defer {
                itemList?.let { mRoomHelper.insertChipItems(it) }
                Single.just(mRoomHelper.getAllChipItems())
            }.subscribeOn(Schedulers.io())
}
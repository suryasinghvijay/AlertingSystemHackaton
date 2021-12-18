package com.gdn.tms.android.alertingsystemhackaton.feature.repository

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseRepository : CoroutineScope {

  val networkCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

  private val job = Job()

  private val repositoryContext = job + Dispatchers.IO

  override val coroutineContext: CoroutineContext
    get() = repositoryContext

  val repositoryScope = CoroutineScope(repositoryContext)

  fun clearRepositoryCoroutineContext() {
    job.cancel()
    coroutineContext.cancel()
  }

}
package com.android.sneakerdroid.presenterpackage

import androidx.work.ListenableWorker

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds

import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass
import dagger.Provides

@Component(
    modules = [
        SampleAssistedInjectModule::class,
        WorkerBindingModule::class,
        AppsDataModule::class

    ]
)
interface SampleComponent {
    fun factory(): SampleWorkerFactory
}

@Module(includes = [AssistedInject_SampleAssistedInjectModule::class])
@AssistedModule
interface SampleAssistedInjectModule

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(MyWorker::class)
    fun MyWorldWorker(factory: MyWorker.Factory): ChildWorkerFactory
}

@Module
class AppsDataModule {

    val settingRepo: AppsData
        @Provides
        get() = AppsData()

}

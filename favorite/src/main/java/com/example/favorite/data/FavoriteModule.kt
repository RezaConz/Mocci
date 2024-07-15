package com.example.favorite.data

import android.content.Context
import com.example.favorite.ui.FavoriteFragment
import com.example.mocci.di.FavoriteModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteModule {
    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun appDependencies(dependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteModule
    }
}
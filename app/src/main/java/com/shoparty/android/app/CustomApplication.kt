package com.shoparty.android.app

import android.app.Application

class CustomApplication : Application() {



    companion object {
        lateinit var application: Application

        fun getInstance(): Application {
            return com.shoparty.android.app.CustomApplication.Companion.application
        }
    }

    /*override val kodein = Kodein.lazy {
        import(androidXModule(this@CustomApplication))

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<ResponseInterceptor>() with singleton { ResponseInterceptorImpl() }
        bind<RegisterRepository>() with singleton { RegisterRepositoryImpl(instance())}

        bind() from provider {
            RegisterVMFactory(
                instance()
            )
        }

        bind<LoginRepository>() with singleton { LoginRepositoryImpl(instance())}

        bind() from provider {
            LoginVMFactory(
                instance()
            )
        }

     //   bind<VerifyOtpRepository>() with singleton { VerifiyOtpRepositoryImpl(instance())}

        bind() from provider {
            VerifiyOtpVMFactory(
                instance()
            )
        }*/


    override fun onCreate() {
        super.onCreate()
        com.shoparty.android.app.CustomApplication.Companion.application = this
    }
}
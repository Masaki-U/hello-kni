import platform.android.*
import kotlinx.cinterop.*

@CName("Java_com_example_hellojni_HelloJni_sayHello")
fun sayHello(test: jfloat){
    __android_log_print(ANDROID_LOG_INFO.toInt(), "Kn", "Hello $test %s", "Native")
}

@CName("Java_com_example_hellojni_HelloJni_sayHello2")
fun sayHello2(test: jint){
    __android_log_print(ANDROID_LOG_INFO.toInt(), "Kn", "Hello $test %s", "Native")
}

@CName("Java_com_example_hellojni_HelloJni_stringFromJNI")
fun stringFromJNI(env: CPointer<JNIEnvVar>, thiz: jobject): jstring {
    memScoped {
        return env.pointed.pointed!!.NewStringUTF!!.invoke(env, "This is from Kotlin Native!!".cstr.ptr)!!
    }
}

@CName("Java_com_example_hellojni_HelloJni_callJava")
fun callJava(env: CPointer<JNIEnvVar>, thiz: jobject, byteBuffer_Plane: AHardwareBuffer_Plane): jstring {
    memScoped {
        val jniEnvVal = env.pointed.pointed!!
        val jclass = jniEnvVal.GetObjectClass!!.invoke(env, thiz)
        val methodId = jniEnvVal.GetMethodID!!.invoke(env, jclass,
                "callFromNative".cstr.ptr, "()Ljava/lang/String;".cstr.ptr)
        return jniEnvVal.CallObjectMethodA!!.invoke(env, thiz, methodId, null) as jstring
    }
}


def can_build(plat):
    return plat == "android" or plat == "iphone"

def configure(env):
    if env['platform'] == 'android':
        env.android_add_dependency("compile files('../../../modules/adbuddiz/android/libs/AdBuddiz-3.1.11.jar')")
        env.android_add_java_dir("android")
        env.android_add_to_manifest("android/AndroidManifestChunk.xml")
    elif env['platform'] == 'iphone':
        env.Append(FRAMEWORKPATH=['modules/adbuddiz/ios/lib'])
        env.Append(LINKFLAGS=['-ObjC', '-framework', 'AdSupport', '-framework', 'AdBuddiz'])
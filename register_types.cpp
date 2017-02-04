#include "register_types.h"
#include "object_type_db.h"
#include "core/globals.h"
#include "ios/src/godotadbuddiz.h"

void register_adbuddiz_types() {
    Globals::get_singleton()->add_singleton(Globals::Singleton("AdBuddiz", memnew(GodotAdbuddiz)));
}

void unregister_adbuddiz_types() {
    
}
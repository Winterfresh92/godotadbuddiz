#ifndef __GODOTADBUDDIZ_H__
#define __GODOTADBUDDIZ_H__

#include "reference.h"

class GodotAdbuddiz : public Reference {
    OBJ_TYPE(GodotAdbuddiz, Reference);

    static void _bind_methods();

    bool initialized;
    int call_id;

public:
    void init(bool test, String publisher_key);

    void setAdCallbackId(int call_id);
    
    void showInterstitial();

    void showRewardedVideo();

    GodotAdbuddiz();
    ~GodotAdbuddiz();
};

#endif
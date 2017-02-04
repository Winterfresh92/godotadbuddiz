#include "godotadbuddiz.h"
#include "core/globals.h"
#include "core/variant.h"
#include "core/message_queue.h"
#import <AdBuddiz/AdBuddiz.h>
#import <UIKit/UIKit.h>

@interface AdBuddizRewardedVideoDelegateBridge : NSObject<AdBuddizRewardedVideoDelegate>

@end

GodotAdbuddiz* instance = NULL;

static AdBuddizRewardedVideoDelegateBridge* delegate = NULL;

void GodotAdbuddiz::init(bool test, String publisher_key) {
    if (!initialized) {

        [AdBuddiz setPublisherKey:publisher_key];

        if (test) {
            [AdBuddiz setTestModeActive];
        }

        [AdBuddiz cacheAds];

        delegate = [[AdBuddizRewardedVideoDelegateBridge alloc] init];
        [AdBuddiz.RewardedVideo setDelegate:delegate];
        [AdBuddiz.RewardedVideo fetch];

        initialized = true;
    }
};

void GodotAdbuddiz::setAdCallbackId(int call_id) {
    if (initialized)
        this->call_id = call_id;
}

void GodotAdbuddiz::showInterstitial() {
    if (initialized)
        [AdBuddiz showAd];
};

void GodotAdbuddiz::showRewardedVideo() {
    if (initialized)
        [AdBuddiz.RewardedVideo show];
};

@implementation AdBuddizRewardedVideoDelegateBridge
- (void)didComplete {

}

@end


void GodotAdbuddiz::_bind_methods() {
    ObjectTypeDB::bind_method(_MD("init"), &GodotAdbuddiz::init);
    ObjectTypeDB::bind_method(_MD("setAdCallbackId"), &GodotAdbuddiz::setAdCallbackId);
    ObjectTypeDB::bind_method(_MD("showRewardedVideo"), &GodotAdbuddiz::showRewardedVideo);
    ObjectTypeDB::bind_method(_MD("showInterstitial"), &GodotAdbuddiz::showInterstitial);
};

GodotAdbuddiz::GodotAdbuddiz() {
    ERR_FAIL_COND(instance != NULL);
    instance = this;
    initialized = false;
};

GodotAdbuddiz::~GodotAdbuddiz() {
    instance = NULL;
};
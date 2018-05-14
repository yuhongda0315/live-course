//
//  ViewController.m
//  RongSDK_iOS
//
//  Created by ZhangLei on 22/03/2018.
//  Copyright © 2018 RongCloud. All rights reserved.
//

#import "ViewController.h"
#import "HttpRequest.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)onInitSDK:(id)sender {
    // 步骤-3：初始化 SDK。appKey 一定要和申请 token 时使用的 appKey 一致。
    [[RCIMClient sharedRCIMClient] initWithAppKey:@"8brlm7ufrg9e3"];
}

// 步骤-4：登录。
- (IBAction)onConnect:(id)sender {
//    [HttpRequest getToken:@"User_B" tokenHandler:^(NSString *token) {
        // 通过 Http 向 AppServer 请求 token。
        NSString *token = @"vRdrw6Mr1e8cPY6vJuU5+x+sHndqNeBH080EFO5XARsVjheEJoaYkoIB08EiA7Amcf0wuO8eciWSCH9hgfur9Q==";
        [[RCIMClient sharedRCIMClient] connectWithToken:token
                                                success:^(NSString *userId) {
                                                    NSLog(@"登陆成功。当前登录的用户ID：%@", userId);
                                                } error:^(RCConnectErrorCode status) {
                                                    NSLog(@"登陆的错误码为:%ld", status);
                                                } tokenIncorrect:^{
                                                    NSLog(@"token错误");
                                                }];
//    }];
}

// 步骤-5：发送消息。
- (IBAction)onSendMsg:(id)sender {
    RCTextMessage *msg = [RCTextMessage messageWithContent:@"Hello User_A"];
    [[RCIMClient sharedRCIMClient] sendMessage:ConversationType_PRIVATE
                                      targetId:@"User_A" content:msg
                                   pushContent:nil pushData:nil
                                       success:nil error:nil];
}

- (IBAction)onSetReceived:(id)sender {
    // 步骤-6-2：接受消息，设置消息监听。
    [[RCIMClient sharedRCIMClient] setReceiveMessageDelegate:self object:nil];
}

// 步骤-6-3：接受消息，实现代理方法。
- (void)onReceived:(RCMessage *)message
              left:(int)nLeft
            object:(id)object {
    if ([message.content isMemberOfClass:[RCTextMessage class]]) {
        RCTextMessage *testMessage = (RCTextMessage *)message.content;
        NSLog(@"消息内容：%@", testMessage.content);
    }
    NSLog(@"还剩余的未接收的消息数：%d", nLeft);
}

// 步骤-7：获取未读消息数。
- (IBAction)onGetUnread:(id)sender {
    int totalUnreadCount = [[RCIMClient sharedRCIMClient] getTotalUnreadCount];
    NSLog(@"当前所有会话的未读消息数为：%d", totalUnreadCount);
}

- (IBAction)onGetConverList:(id)sender {
    NSArray *conversationList = [[RCIMClient sharedRCIMClient]
                                 getConversationList:@[@(ConversationType_PRIVATE),
                                                       @(ConversationType_DISCUSSION),
                                                       @(ConversationType_GROUP),
                                                       @(ConversationType_SYSTEM),
                                                       @(ConversationType_APPSERVICE),
                                                       @(ConversationType_PUBLICSERVICE)]];
    for (RCConversation *conversation in conversationList) {
        NSLog(@"会话类型：%lu，目标会话ID：%@", (unsigned long)conversation.conversationType, conversation.targetId);
    }
}

@end

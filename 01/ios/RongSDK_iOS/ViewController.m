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

// 步骤-6，实现代理方法
- (void)onReceived:(RCMessage *)message
              left:(int)nLeft
            object:(id)object {
    if ([message.content isMemberOfClass:[RCTextMessage class]]) {
        RCTextMessage *testMessage = (RCTextMessage *)message.content;
        NSLog(@"消息内容：%@", testMessage.content);
    }
    NSLog(@"还剩余的未接收的消息数：%d", nLeft);
}

- (IBAction)onInit:(id)sender {
    // 步骤-3，初始化 SDK
    [[RCIMClient sharedRCIMClient] initWithAppKey:@"8brlm7ufrg9e3"];
    
    // 步骤-5，设置消息监听
    [[RCIMClient sharedRCIMClient] setReceiveMessageDelegate:self object:nil];
}

// 步骤-4，登录
- (IBAction)onConnect:(id)sender {
    [HttpRequest getToken:@"User_B" tokenHandler:^(NSString *token) {
        [[RCIMClient sharedRCIMClient] connectWithToken:token
                                                success:^(NSString *userId) {
                                                    NSLog(@"登陆成功。当前登录的用户ID：%@", userId);
                                                } error:^(RCConnectErrorCode status) {
                                                    NSLog(@"登陆的错误码为:%ld", status);
                                                } tokenIncorrect:^{
                                                    NSLog(@"token错误");
                                                }];
    }];
}

// 步骤-7，发送消息
- (IBAction)onSend:(id)sender {
    RCTextMessage *msg = [RCTextMessage messageWithContent:@"Hello User_A"];
    [[RCIMClient sharedRCIMClient] sendMessage:ConversationType_PRIVATE
                                      targetId:@"User_A" content:msg
                                   pushContent:nil pushData:nil
                                       success:nil error:nil];
}

@end

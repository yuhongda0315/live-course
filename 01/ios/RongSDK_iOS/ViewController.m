//
//  ViewController.m
//  RongSDK_iOS
//
//  Created by ZhangLei on 22/03/2018.
//  Copyright © 2018 RongCloud. All rights reserved.
//

// 步骤-2，添加融云头文件
#import <RongIMKit/RongIMKit.h>

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

- (IBAction)onInit:(id)sender {
    // 步骤-3，初始化 SDK
    [[RCIM sharedRCIM] initWithAppKey:@"8luwapkvucoil"];
}

- (IBAction)onConnect:(id)sender {
    // 步骤-4，登录
    [HttpRequest getToken:^(NSString *token) {
        NSLog(@"token = %@", token);
        [[RCIM sharedRCIM] connectWithToken:token
                                    success:^(NSString *userId) {
                                        NSLog(@"登陆成功。当前登录的用户ID：%@", userId);
                                        //                                    dispatch_async(dispatch_get_main_queue(), ^{
                                        //                                        ConversationListViewController *conList = ConversationListViewController.new;
                                        //                                        [self.navigationController pushViewController:conList animated:YES];
                                        //                                    });
                                    } error:^(RCConnectErrorCode status) {
                                        NSLog(@"登陆的错误码为:%ld", status);
                                    } tokenIncorrect:^{
                                        NSLog(@"token错误");
                                    }];
    }];

}

@end

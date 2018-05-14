//
//  ViewController.h
//  RongSDK_iOS
//
//  Created by ZhangLei on 22/03/2018.
//  Copyright © 2018 RongCloud. All rights reserved.
//

#import <UIKit/UIKit.h>

// 步骤-2：添加融云 SDK 头文件。
#import <RongIMLib/RongIMLib.h>

// 步骤-6-1：接受消息，声明代理的使用者。
@interface ViewController : UIViewController<RCIMClientReceiveMessageDelegate>

@end


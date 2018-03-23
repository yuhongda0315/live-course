//
//  HttpRequest.h
//  RongSDK_iOS
//
//  Created by ZhangLei on 22/03/2018.
//  Copyright © 2018 RongCloud. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface HttpRequest : NSObject

+ (void)getToken:(NSString *)userId tokenHandler:(void (^)(NSString *token))tokenHandler;

@end

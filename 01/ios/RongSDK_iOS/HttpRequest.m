//
//  HttpRequest.m
//  RongSDK_iOS
//
//  Created by ZhangLei on 22/03/2018.
//  Copyright © 2018 RongCloud. All rights reserved.
//

#import "HttpRequest.h"

@implementation HttpRequest

+ (void)getToken:(void (^)(NSString *token))tokenHandler {
    NSMutableURLRequest *request =
    [NSMutableURLRequest requestWithURL:[NSURL URLWithString:@"http://localhost:8585/get_token"] cachePolicy:0 timeoutInterval:60.0f];
    request.HTTPMethod = @"GET";
    NSURLSessionDataTask *dataTask = [[NSURLSession sharedSession]
                                      dataTaskWithRequest:request
                                      completionHandler:^(NSData *_Nullable data, NSURLResponse *_Nullable response, NSError *_Nullable error) {
                                          NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:&error];
                                          tokenHandler([dict objectForKey:@"token"]);
                                      }];
    [dataTask resume];
}

@end

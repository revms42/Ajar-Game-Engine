//
//  SDLImage.m
//  Mech
//
//  Created by Andrew Parker on 4/14/07.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

#import "SDLImage.h"
#import "SDLScreen.h"

#include "SDL.h"
#include "SDL_image.h"

@implementation SDLImage

-(id)initWithFile: (NSString *)file {
	self = [super init];

	filename = [[NSString alloc] initWithString: file];
	
	image = IMG_Load([filename cStringUsingEncoding: [NSString defaultCStringEncoding]]);
	
	return self;
}

-(void)drawOnScreen: (SDLScreen *)screen {
	SDL_Rect rcDest = { 0, 0, 0, 0 };
	[screen blit: image at: &rcDest];
}

@end

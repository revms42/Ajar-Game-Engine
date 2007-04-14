//
//  SDLImage.h
//  Mech
//
//  Created by Andrew Parker on 4/14/07.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "SDLScreen.h"

#include "SDL.h"

@interface SDLImage : NSObject {
	NSString *filename;
	SDL_Surface *image;
}

-(id)initWithFile: (NSString *)file;
-(void)drawOnScreen: (SDLScreen *)screen;

@end

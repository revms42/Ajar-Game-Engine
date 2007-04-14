//
//  Screen.h
//  Mech
//
//  Created by Andrew Parker on 4/12/07.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

#import <Cocoa/Cocoa.h>

#include "SDL.h"

@interface SDLScreen : NSObject {
	SDL_Surface *screen;
}

-(SDLScreen *) initWithSurface: (SDL_Surface *)surface;
-(void) flip;
-(void) blit: (SDL_Surface *)surface at: (SDL_Rect *)dest;

@end

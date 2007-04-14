//
//  Screen.m
//  Mech
//
//  Created by Andrew Parker on 4/12/07.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

#import "SDLScreen.h"

@implementation SDLScreen

-(SDLScreen *) initWithSurface: (SDL_Surface *)surface {
	self = [super init];
	
	screen = surface;
	
	return self;
}

-(void) flip {
	SDL_Flip(screen);
}

-(void) blit: (SDL_Surface *)surface at: (SDL_Rect *)dest {
	SDL_BlitSurface(surface, NULL, screen, dest );
}

@end

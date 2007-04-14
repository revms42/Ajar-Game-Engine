//
//  handlers.m
//  Mech
//
//  Created by Andrew Parker on 4/12/07.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

#import "SDLScreen.h"
#import "SDLImage.h"

#include "SDL.h"

SDLScreen *screen;
bool shouldContinue = TRUE;

void handleEvent(SDL_Event *event) {
	if(event->type == SDL_KEYDOWN && event->key.keysym.sym == SDLK_q) {
		shouldContinue = FALSE;
	}
}

bool shouldContinueHandlingEvents() {
	return shouldContinue;
}

void init(SDL_Surface *scn) {
	screen = [[SDLScreen alloc] initWithSurface: scn];
	NSString *markFile = [[NSBundle mainBundle] pathForImageResource: @"mark.jpeg"];
	SDLImage *mark = [[SDLImage alloc] initWithFile: markFile];
	[mark drawOnScreen: screen];
	[screen flip];
	[mark release];
	[markFile release];
}

void release() {
	[screen release];
}
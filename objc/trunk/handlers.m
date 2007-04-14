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
	if(event->type == SDL_KEYDOWN) {
		shouldContinue = FALSE;
	}
}

bool shouldContinueHandlingEvents() {
	return shouldContinue;
}

void init(SDL_Surface *scn) {
	screen = [[SDLScreen alloc] initWithSurface: scn];
	SDLImage *mark = [[SDLImage alloc] initWithFile: [[NSBundle mainBundle] pathForResource:@"Photo 2" ofType:@"JPG"]];
	[mark drawOnScreen: screen];
	[screen flip];
	[mark release];
}

void release() {
	[screen release];
}
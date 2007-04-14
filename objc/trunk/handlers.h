//
//  handlers.h
//  Mech
//
//  Created by Andrew Parker on 4/12/07.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//

#include <objc/objc.h>
#include "SDL.h"

void handleEvent(SDL_Event *event);
BOOL shouldContinueHandlingEvents();
void init(SDL_Surface *screen);
void release();
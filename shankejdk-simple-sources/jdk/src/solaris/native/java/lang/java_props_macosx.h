/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#include "java_props.h"

char *setupMacOSXLocale(int cat);
void setOSNameAndVersion(java_props_t *sprops);
void setUserHome(java_props_t *sprops);
void setProxyProperties(java_props_t *sProps);
int isInAquaSession();

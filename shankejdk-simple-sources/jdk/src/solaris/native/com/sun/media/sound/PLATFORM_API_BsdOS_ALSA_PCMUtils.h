/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
 */

// define this with a later version of ALSA than 0.9.0rc3
// (starting from 1.0.0 it became default behaviour)
#define ALSA_PCM_NEW_HW_PARAMS_API
#include <alsa/asoundlib.h>
#include "Utilities.h"

#ifndef PLATFORM_API_BSDOS_ALSA_PCMUTILS_H_INCLUDED
#define PLATFORM_API_BSDOS_ALSA_PCMUTILS_H_INCLUDED

// if this is defined, use plughw: devices
#define ALSA_PCM_USE_PLUGHW
//#undef ALSA_PCM_USE_PLUGHW


// maximum number of channels that is listed in the formats. If more, than
// just -1 for channel count is used.
#define MAXIMUM_LISTED_CHANNELS 32

typedef struct tag_ALSA_AudioDeviceDescription {
    int index;          // in
    int strLen;         // in
    INT32* deviceID;    // out
    int* maxSimultaneousLines; // out
    char* name;         // out
    char* vendor;       // out
    char* description;  // out
    char* version;      // out
} ALSA_AudioDeviceDescription;



int getAudioDeviceCount();
int getAudioDeviceDescriptionByIndex(ALSA_AudioDeviceDescription* desc);

// returns ALSA error code, or 0 if successful
int openPCMfromDeviceID(int deviceID, snd_pcm_t** handle, int isSource, int hardware);

// returns 1 if successful
// enc: 0 for PCM, 1 for ULAW, 2 for ALAW (see DirectAudio.h)
int getFormatFromAlsaFormat(snd_pcm_format_t alsaFormat,
                            int* sampleSizeInBytes, int* significantBits,
                            int* isSigned, int* isBigEndian, int* enc);

int getAlsaFormatFromFormat(snd_pcm_format_t* alsaFormat,
                            int sampleSizeInBytes, int significantBits,
                            int isSigned, int isBigEndian, int enc);

#endif // PLATFORM_API_BSDOS_ALSA_PCMUTILS_H_INCLUDED

/*
 * Copyright (c) 2001, 2014, Oracle and/or its affiliates. All rights reserved.
 */

#define ushort unsigned short
#define uint   unsigned int
#define uchar  unsigned char

struct unpacker;

struct jar {
  // JAR file writer
  FILE*       jarfp;
  int         default_modtime;

  // Used by unix2dostime:
  int         modtime_cache;
  uLong       dostime_cache;

  // Private members
  fillbytes   central_directory;
  uint        central_directory_count;
  uint        output_file_offset;
  fillbytes   deflated;  // temporary buffer

  // pointer to outer unpacker, for error checks etc.
  unpacker* u;

  // Public Methods
  void openJarFile(const char* fname);
  void addJarEntry(const char* fname,
                   bool deflate_hint, int modtime,
                   bytes& head, bytes& tail);
  void addDirectoryToJarFile(const char* dir_name);
  void closeJarFile(bool central);

  void init(unpacker* u_);

  void free() {
    central_directory.free();
    deflated.free();
  }

  void reset() {
    free();
    init(u);
  }

  // Private Methods
  void write_data(void* ptr, int len);
  void write_data(bytes& b) { write_data(b.ptr, (int)b.len); }
  void add_to_jar_directory(const char* fname, bool store, int modtime,
                            int len, int clen, uLong crc);
  void write_jar_header(const char* fname, bool store, int modtime,
                        int len, int clen, unsigned int crc);
  void write_jar_extra(int len, int clen, unsigned int crc);
  void write_central_directory();
  uLong dostime(int y, int n, int d, int h, int m, int s);
  uLong get_dostime(int modtime);

  // The definitions of these depend on the NO_ZLIB option:
  bool deflate_bytes(bytes& head, bytes& tail);
  static uint get_crc32(uint c, unsigned char *ptr, uint len);

  // error handling
  void abort(const char* msg) { unpack_abort(msg, u); }
  bool aborting()             { return unpack_aborting(u); }
};

struct gunzip {
  // optional gzip input stream control block

  // pointer to outer unpacker, for error checks etc.
  unpacker* u;

  void* read_input_fn;  // underlying byte stream
  void* zstream;        // inflater state
  char inbuf[1 << 14];   // input buffer

  void init(unpacker* u_);  // pushes new value on u->read_input_fn

  void free();

  void start(int magic);

  // private stuff
  void read_fixed_field(char* buf, size_t buflen);

  // error handling
  void abort(const char* msg) { unpack_abort(msg, u); }
  bool aborting()             { return unpack_aborting(u); }
};

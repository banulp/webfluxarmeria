package com.banulp.grpc;

final class BlogNotFoundException extends IllegalStateException {
    BlogNotFoundException(String s) {
        super(s);
    }
}
package com.sparta.harmony.common.logging;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LoggingHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private final CharArrayWriter charArrayWriter = new CharArrayWriter();
    private final PrintWriter writer = new PrintWriter(charArrayWriter);
    private int status;

    public LoggingHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void setStatus(int statusCode) {
        super.setStatus(statusCode);
        this.status = statusCode;
    }

    public String getContent() {
        return charArrayWriter.toString();
    }

    public int getStatus() {
        return status;
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
        super.getWriter().write(getContent());
        super.flushBuffer();
    }
}

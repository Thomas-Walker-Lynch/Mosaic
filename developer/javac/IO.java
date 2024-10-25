package com.ReasoningTechnology.Mosaic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;

public class IO {

    private PrintStream original_out;
    private PrintStream original_err;
    private InputStream original_in;

    private ByteArrayOutputStream out_content;
    private ByteArrayOutputStream err_content;
    private ByteArrayInputStream in_content;

    public void redirect_io(String input_data){
        original_out = System.out;
        original_err = System.err;
        original_in = System.in;

        out_content = new ByteArrayOutputStream();
        err_content = new ByteArrayOutputStream();
        in_content = new ByteArrayInputStream(input_data.getBytes());

        System.setOut(new PrintStream(out_content));
        System.setErr(new PrintStream(err_content));
        System.setIn(in_content);
    }

    public void restore_io(){
        // Flush the output streams to prevent carrying over data
        flush_buffers();

        System.setOut(original_out);
        System.setErr(original_err);
        System.setIn(original_in);
    }

    public void clear_buffers(){
        out_content.reset();
        err_content.reset();
    }

    public void flush_buffers(){
        // Clear the buffers for the next use
        out_content.reset();
        err_content.reset();
    }

    public ByteArrayInputStream get_in_content(){
        return in_content;
    }

    public ByteArrayOutputStream get_out_content(){
        return out_content;
    }

    public ByteArrayOutputStream get_err_content(){
        return err_content;
    }
}

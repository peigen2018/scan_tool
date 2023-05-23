package com.pg.burpextender.component;

import burp.IMessageEditorController;
import burp.IMessageEditorTab;
import burp.IMessageEditorTabFactory;
import burp.ITextEditor;

import java.awt.*;

public class MessageEditor implements IMessageEditorTab, IMessageEditorTabFactory {

    ITextEditor textEditor;

    public MessageEditor(ITextEditor textEditor) {
        this.textEditor = textEditor;
    }

    @Override
    public String getTabCaption() {
        return "GRPC";
    }

    @Override
    public Component getUiComponent() {
        return textEditor.getComponent();
    }

    @Override
    public boolean isEnabled(byte[] content, boolean isRequest) {
        return true;
    }

    @Override
    public void setMessage(byte[] content, boolean isRequest) {

    }

    @Override
    public byte[] getMessage() {
        return new byte[0];
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public byte[] getSelectedData() {
        return new byte[0];
    }

    @Override
    public IMessageEditorTab createNewInstance(IMessageEditorController controller, boolean editable) {
        return this;
    }
}

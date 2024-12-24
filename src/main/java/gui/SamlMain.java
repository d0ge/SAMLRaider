package gui;

import application.SamlTabController;
import burp.BurpExtender;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.ui.editor.RawEditor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static java.util.Objects.requireNonNull;

public class SamlMain extends JPanel {

    private final SamlTabController controller;

    private RawEditor textEditorAction;
    private RawEditor textEditorInformation;
    private SamlPanelAction panelAction;
    private SamlPanelInfo panelInformation;

    public SamlMain(SamlTabController controller) {
        this.controller = requireNonNull(controller, "controller");
        initializeUI();
    }

    private void initializeUI() {
        panelAction = new SamlPanelAction(controller);

        JPanel panelActionTop = new JPanel();
        panelActionTop.setLayout(new BorderLayout());
        panelActionTop.add(panelAction);

        textEditorAction = BurpExtender.api.userInterface().createRawEditor();
        textEditorAction.setContents(ByteArray.byteArray("<SAMLRaiderFailureInInitialization></SAMLRaiderFailureInInitialization>"));
        textEditorAction.setEditable(false);

        JPanel panelActionBottom = new JPanel();
        panelActionBottom.setLayout(new BorderLayout());
        panelActionBottom.add(textEditorAction.uiComponent(), BorderLayout.CENTER);

        JSplitPane splitPaneAction = new JSplitPane();
        splitPaneAction.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPaneAction.setLeftComponent(panelActionTop);
        splitPaneAction.setRightComponent(panelActionBottom);

        panelInformation = new SamlPanelInfo();

        JPanel panelInformationTop = new JPanel();
        panelInformationTop.setLayout(new BorderLayout());
        panelInformationTop.add(panelInformation);
        panelInformationTop.setPreferredSize(new Dimension(0, 375));

        textEditorInformation = BurpExtender.api.userInterface().createRawEditor();
        textEditorInformation.setContents(ByteArray.byteArray(""));

        var panelInformationBottomLabel = new JLabel("Parsed & Prettified");
        panelInformationBottomLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel panelInformationBottom = new JPanel();
        panelInformationBottom.setLayout(new BorderLayout());
        panelInformationBottom.add(panelInformationBottomLabel, BorderLayout.NORTH);
        panelInformationBottom.add(textEditorInformation.uiComponent(), BorderLayout.CENTER);
        panelInformationBottom.setPreferredSize(new Dimension(0, 100));

        JSplitPane splitPaneInformation = new JSplitPane();
        splitPaneInformation.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPaneInformation.setLeftComponent((panelInformationTop));
        splitPaneInformation.setRightComponent(panelInformationBottom);
        splitPaneInformation.resetToPreferredSizes();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("SAML Attacks", null, splitPaneAction, "SAML Attacks");
        tabbedPane.addTab("SAML Message Info", null, splitPaneInformation, "SAML Message Info");

        setLayout(new BorderLayout());
        add(tabbedPane);

        invalidate();
        updateUI();
    }

    public RawEditor getTextEditorAction() {
        return textEditorAction;
    }

    public RawEditor getTextEditorInformation() {
        return textEditorInformation;
    }

    public SamlPanelAction getActionPanel() {
        return panelAction;
    }

    public SamlPanelInfo getInfoPanel() {
        return panelInformation;
    }

}

package com.sombrasdelavismo;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Main extends JFrame {

    private static final List<String> CARD_FILES = Arrays.asList(
            "createcard Fernando.jpg",
            "createcard GONZALO BUENA.jpg",
            "createcard Humoo.jpg",
            "createcard Lin.jpg",
            "createcard Manuel.jpg",
            "createcard Pandaa.jpg",
            "createcard adrian.jpg",
            "createcard alonsoo.jpg",
            "createcard alvaro.jpg",
            "createcard amayita.jpg",
            "createcard antonioo.jpg",
            "createcard apuestas.jpg",
            "createcard bartol.jpg",
            "createcard carmenn.jpg",
            "createcard carpazoo.jpg",
            "createcard crespo.jpg",
            "createcard dieguitoo.jpg",
            "createcard elgrito.jpg",
            "createcard enchino.jpg",
            "createcard fabio.jpg",
            "createcard fran.jpg",
            "createcard fueguitoo.jpg",
            "createcard hackear.jpg",
            "createcard hugooo.jpg",
            "createcard inees.jpg",
            "createcard jaime.jpg",
            "createcard javi medio.jpg",
            "createcard juanma.jpg",
            "createcard kung fu.jpg",
            "createcard luismiii.jpg",
            "createcard mamen.jpg",
            "createcard marco.jpg",
            "createcard marcooo.jpg",
            "createcard marito.jpg",
            "createcard monof1.jpg",
            "createcard oviedo.jpg",
            "createcard pedro.jpg",
            "createcard quemao.jpg",
            "createcard rodri.jpg",
            "createcard urbano.jpg"
    );

    private final Game game;
    private final List<Card> collection;
    private Card selectedCard;

    private final JLabel titleStatusLabel = new JLabel();
    private final JLabel turnLabel = new JLabel();
    private final JLabel currentPlayerLabel = new JLabel();
    private final JLabel opponentLabel = new JLabel();
    private final JLabel deckLabel = new JLabel();
    private final JLabel hintLabel = new JLabel();
    private final JLabel selectedImageLabel = new JLabel();
    private final JLabel selectedNameLabel = new JLabel();
    private final JLabel selectedMetaLabel = new JLabel();
    private final JLabel selectedDescriptionLabel = new JLabel();
    private final JPanel handPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
    private final JPanel collectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));

    public Main() {
        collection = buildCollection();

        Player player1 = new Player("Jugador 1");
        Player player2 = new Player("Jugador 2");
        dealCards(player1, player2, collection);

        game = new Game(player1, player2);
        game.startGame();
        selectedCard = game.getCurrentPlayer().getHand().get(0);

        configureWindow();
        buildInterface();
        refreshUi(selectedCard);
    }

    private void configureWindow() {
        setTitle("Sombras del Abismo");
        setSize(1480, 920);
        setMinimumSize(new Dimension(1200, 780));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(new GradientPanel());
    }

    private void buildInterface() {
        JPanel root = (JPanel) getContentPane();
        root.setLayout(new BorderLayout(18, 18));
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildMainArea(), BorderLayout.CENTER);
        root.add(buildBottomTabs(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        JPanel panel = createPanel(new BorderLayout(14, 0), new Color(10, 17, 29, 225));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 179, 80, 90), 1, true),
                BorderFactory.createEmptyBorder(16, 22, 16, 22)
        ));

        JLabel title = new JLabel("Sombras del Abismo");
        title.setForeground(new Color(244, 232, 203));
        title.setFont(new Font("Palatino Linotype", Font.BOLD, 32));

        JLabel subtitle = new JLabel("Selecciona una carta desde tu mano o desde la coleccion inferior");
        subtitle.setForeground(new Color(194, 207, 219));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(subtitle);

        titleStatusLabel.setForeground(new Color(255, 217, 130));
        titleStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleStatusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(left, BorderLayout.WEST);
        panel.add(titleStatusLabel, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildMainArea() {
        JPanel panel = new JPanel(new BorderLayout(18, 18));
        panel.setOpaque(false);
        panel.add(buildSidebar(), BorderLayout.WEST);
        panel.add(buildCardDetail(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = createPanel(new BorderLayout(0, 16), new Color(10, 23, 38, 215));
        sidebar.setPreferredSize(new Dimension(300, 0));
        sidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 129, 158, 90), 1, true),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel panelTitle = new JLabel("Panel de partida");
        panelTitle.setForeground(new Color(241, 232, 206));
        panelTitle.setFont(new Font("Palatino Linotype", Font.BOLD, 24));

        turnLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        turnLabel.setForeground(new Color(255, 218, 129));
        currentPlayerLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        currentPlayerLabel.setForeground(new Color(229, 236, 245));
        opponentLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        opponentLabel.setForeground(new Color(205, 214, 224));
        deckLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        deckLabel.setForeground(new Color(205, 214, 224));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(panelTitle);
        infoPanel.add(Box.createVerticalStrut(18));
        infoPanel.add(createInfoCard("Turno", turnLabel));
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(createInfoCard("Jugador actual", currentPlayerLabel));
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(createInfoCard("Rival", opponentLabel));
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(createInfoCard("Mazo", deckLabel));

        JButton drawButton = createPrimaryButton("Robar carta");
        drawButton.addActionListener(event -> {
            int previousSize = game.getCurrentPlayer().getHand().size();
            game.getCurrentPlayer().drawCard();
            if (game.getCurrentPlayer().getHand().size() > previousSize) {
                selectedCard = game.getCurrentPlayer().getHand().get(game.getCurrentPlayer().getHand().size() - 1);
            }
            refreshUi(selectedCard);
        });

        JButton nextTurnButton = createSecondaryButton("Pasar turno");
        nextTurnButton.addActionListener(event -> {
            game.nextTurn();
            if (!game.getCurrentPlayer().getHand().isEmpty()) {
                selectedCard = game.getCurrentPlayer().getHand().get(0);
            }
            refreshUi(selectedCard);
        });

        hintLabel.setForeground(new Color(187, 200, 214));
        hintLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        hintLabel.setVerticalAlignment(SwingConstants.TOP);

        JPanel actionPanel = new JPanel();
        actionPanel.setOpaque(false);
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        drawButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextTurnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        hintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionPanel.add(drawButton);
        actionPanel.add(Box.createVerticalStrut(10));
        actionPanel.add(nextTurnButton);
        actionPanel.add(Box.createVerticalStrut(18));
        actionPanel.add(hintLabel);

        sidebar.add(infoPanel, BorderLayout.NORTH);
        sidebar.add(actionPanel, BorderLayout.SOUTH);
        return sidebar;
    }

    private JPanel buildCardDetail() {
        JPanel detail = createPanel(new BorderLayout(16, 16), new Color(27, 18, 11, 220));
        detail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 183, 88, 95), 1, true),
                BorderFactory.createEmptyBorder(22, 22, 22, 22)
        ));

        JLabel detailTitle = new JLabel("Carta seleccionada");
        detailTitle.setForeground(new Color(247, 233, 201));
        detailTitle.setFont(new Font("Palatino Linotype", Font.BOLD, 28));

        selectedImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectedImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        selectedImageLabel.setPreferredSize(new Dimension(460, 470));
        selectedImageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 212, 110, 90), 1, true),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));

        selectedNameLabel.setForeground(new Color(255, 229, 175));
        selectedNameLabel.setFont(new Font("Palatino Linotype", Font.BOLD, 30));
        selectedMetaLabel.setForeground(new Color(231, 232, 238));
        selectedMetaLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        selectedDescriptionLabel.setForeground(new Color(222, 226, 232));
        selectedDescriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        selectedDescriptionLabel.setVerticalAlignment(SwingConstants.TOP);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(selectedNameLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(selectedMetaLabel);
        textPanel.add(Box.createVerticalStrut(14));
        textPanel.add(selectedDescriptionLabel);

        detail.add(detailTitle, BorderLayout.NORTH);
        detail.add(selectedImageLabel, BorderLayout.CENTER);
        detail.add(textPanel, BorderLayout.SOUTH);
        return detail;
    }

    private JTabbedPane buildBottomTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabs.setBackground(new Color(10, 18, 28));
        tabs.addTab("Tu mano", buildHandTab());
        tabs.addTab("Coleccion completa", buildCollectionTab());
        return tabs;
    }

    private JScrollPane buildHandTab() {
        handPanel.setOpaque(false);

        JPanel wrapper = createPanel(new BorderLayout(0, 10), new Color(8, 14, 24, 225));
        wrapper.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JLabel title = new JLabel("Tus cartas disponibles");
        title.setForeground(new Color(238, 229, 201));
        title.setFont(new Font("Palatino Linotype", Font.BOLD, 22));

        JLabel text = new JLabel("Haz clic en cualquier carta para verla en grande.");
        text.setForeground(new Color(194, 205, 219));
        text.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);
        header.add(title);
        header.add(text);

        wrapper.add(header, BorderLayout.NORTH);
        wrapper.add(handPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        styleScrollPane(scrollPane);
        scrollPane.setPreferredSize(new Dimension(0, 250));
        return scrollPane;
    }

    private JScrollPane buildCollectionTab() {
        collectionPanel.setOpaque(false);

        JPanel wrapper = createPanel(new BorderLayout(0, 10), new Color(8, 14, 24, 225));
        wrapper.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JLabel title = new JLabel("Todos los personajes");
        title.setForeground(new Color(238, 229, 201));
        title.setFont(new Font("Palatino Linotype", Font.BOLD, 22));

        JLabel text = new JLabel("Usa esta galeria para localizar cualquier carta del zip.");
        text.setForeground(new Color(194, 205, 219));
        text.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);
        header.add(title);
        header.add(text);

        for (Card card : collection) {
            collectionPanel.add(createCollectionCard(card));
        }

        wrapper.add(header, BorderLayout.NORTH);
        wrapper.add(collectionPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        styleScrollPane(scrollPane);
        scrollPane.setPreferredSize(new Dimension(0, 250));
        return scrollPane;
    }

    private JPanel createInfoCard(String title, JLabel valueLabel) {
        JPanel panel = createPanel(new BorderLayout(0, 8), new Color(15, 31, 49, 220));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 120, 149, 70), 1, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(new Color(170, 188, 205));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createHandCard(Card card) {
        JPanel panel = createPanel(new BorderLayout(0, 8), new Color(15, 27, 43, 225));
        panel.setPreferredSize(new Dimension(170, 230));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isSelected(card) ? new Color(255, 205, 102) : new Color(91, 122, 150, 90), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel imageLabel = new JLabel(loadCardImage(card, 140, 145));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel("<html><center>" + card.getName() + "</center></html>", SwingConstants.CENTER);
        nameLabel.setForeground(new Color(247, 236, 206));
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel metaLabel = new JLabel("<html><center>" + compactMeta(card) + "</center></html>", SwingConstants.CENTER);
        metaLabel.setForeground(new Color(205, 214, 225));
        metaLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JButton button = createPrimaryButton("Ver");
        button.setMargin(new Insets(6, 12, 6, 12));
        button.addActionListener(event -> {
            selectedCard = card;
            refreshUi(selectedCard);
        });

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        metaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottom.add(nameLabel);
        bottom.add(Box.createVerticalStrut(4));
        bottom.add(metaLabel);
        bottom.add(Box.createVerticalStrut(8));
        bottom.add(button);

        panel.add(imageLabel, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCollectionCard(Card card) {
        JPanel panel = createPanel(new BorderLayout(0, 8), new Color(23, 23, 23, 220));
        panel.setPreferredSize(new Dimension(162, 184));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isSelected(card) ? new Color(255, 205, 102) : new Color(217, 181, 90, 85), 2, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel imageLabel = new JLabel(loadCardImage(card, 136, 104));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel("<html><center>" + card.getName() + "</center></html>", SwingConstants.CENTER);
        nameLabel.setForeground(new Color(245, 233, 198));
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        JButton button = createSecondaryButton("Seleccionar");
        button.setMargin(new Insets(6, 10, 6, 10));
        button.addActionListener(event -> {
            selectedCard = card;
            refreshUi(selectedCard);
        });

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottom.add(nameLabel);
        bottom.add(Box.createVerticalStrut(8));
        bottom.add(button);

        panel.add(imageLabel, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.CENTER);
        return panel;
    }

    private void refreshUi(Card cardToShow) {
        titleStatusLabel.setText("Turno " + game.getTurnNumber() + "  |  " + game.getCurrentPlayer().getName());
        turnLabel.setText("Turno " + game.getTurnNumber());
        currentPlayerLabel.setText(game.getCurrentPlayer().getName() + " con " + game.getCurrentPlayer().getHand().size() + " cartas en mano");
        opponentLabel.setText(game.getWaitingPlayer().getName() + " con " + game.getWaitingPlayer().getHand().size() + " cartas en mano");
        deckLabel.setText(game.getCurrentPlayer().getDeckSize() + " cartas pendientes de robar");
        hintLabel.setText("<html><body style='width:230px'>Usa <b>Robar carta</b> para anadir otra carta a tu mano o <b>Pasar turno</b> para cambiar al siguiente jugador.</body></html>");

        selectedNameLabel.setText(cardToShow.getName());
        selectedMetaLabel.setText(buildMeta(cardToShow));
        selectedDescriptionLabel.setText("<html><body style='width:520px'>" + cardToShow.getDescription() + "</body></html>");
        selectedImageLabel.setIcon(loadCardImage(cardToShow, 430, 430));
        selectedImageLabel.setText(selectedImageLabel.getIcon() == null ? "Imagen no disponible" : "");

        rebuildHand();
        rebuildCollectionHighlights();
    }

    private void rebuildHand() {
        handPanel.removeAll();
        for (Card card : game.getCurrentPlayer().getHand()) {
            handPanel.add(createHandCard(card));
        }
        handPanel.revalidate();
        handPanel.repaint();
    }

    private void rebuildCollectionHighlights() {
        collectionPanel.removeAll();
        for (Card card : collection) {
            collectionPanel.add(createCollectionCard(card));
        }
        collectionPanel.revalidate();
        collectionPanel.repaint();
    }

    private boolean isSelected(Card card) {
        return selectedCard != null && selectedCard.getName().equals(card.getName());
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(193, 137, 46));
        button.setForeground(new Color(250, 247, 241));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(43, 72, 103));
        button.setForeground(new Color(241, 245, 249));
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        return button;
    }

    private JPanel createPanel(BorderLayout layout, Color color) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(color);
        return panel;
    }

    private void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(14);
    }

    private List<Card> buildCollection() {
        List<Card> cards = new ArrayList<>();
        for (String fileName : CARD_FILES) {
            cards.add(createCard(fileName));
        }
        return cards;
    }

    private void dealCards(Player player1, Player player2, List<Card> cards) {
        for (int index = 0; index < cards.size(); index++) {
            if (index % 2 == 0) {
                player1.addCardToDeck(cards.get(index));
            } else {
                player2.addCardToDeck(cards.get(index));
            }
        }
    }

    private Card createCard(String fileName) {
        String name = prettifyName(fileName);
        String imagePath = "cards/" + fileName;
        int cost = 1 + Math.floorMod(name.length(), 6);

        if (isSpellCard(name)) {
            String effect = "Activa la habilidad especial de " + name + " y cambia el ritmo de la partida.";
            return new SpellCard(name, cost, effect, imagePath);
        }

        int power = 2 + Math.floorMod(name.hashCode(), 5);
        int toughness = 2 + Math.floorMod(name.hashCode() / 7, 5);
        String description = name + " entra en escena como una carta legendaria personalizada con presencia propia en el tablero.";
        return new CreatureCard(name, cost, power, toughness, description, imagePath);
    }

    private boolean isSpellCard(String name) {
        String normalized = name.toLowerCase(Locale.ROOT);
        return normalized.contains("hackear")
                || normalized.contains("apuestas")
                || normalized.contains("elgrito")
                || normalized.contains("kung fu")
                || normalized.contains("fueguitoo")
                || normalized.contains("humoo")
                || normalized.contains("monof1");
    }

    private String prettifyName(String fileName) {
        String cleanName = fileName.replace("createcard", "")
                .replace(".jpg", "")
                .replace(".jpeg", "")
                .trim()
                .replaceAll("\\s+", " ");

        if (cleanName.equals(cleanName.toUpperCase(Locale.ROOT)) || cleanName.equals(cleanName.toLowerCase(Locale.ROOT))) {
            String[] parts = cleanName.toLowerCase(Locale.ROOT).split(" ");
            StringBuilder builder = new StringBuilder();
            for (String part : parts) {
                if (part.isBlank()) {
                    continue;
                }
                if (builder.length() > 0) {
                    builder.append(' ');
                }
                builder.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
            }
            return builder.toString();
        }

        return cleanName;
    }

    private String buildMeta(Card card) {
        if (card instanceof CreatureCard creature) {
            return "Tipo: " + card.getType() + "  |  Coste: " + card.getCost() + "  |  Ataque/Vida: " + creature.getPower() + "/" + creature.getToughness();
        }
        if (card instanceof SpellCard spellCard) {
            return "Tipo: " + card.getType() + "  |  Coste: " + card.getCost() + "  |  Efecto: " + spellCard.getEffect();
        }
        return "Tipo: " + card.getType() + "  |  Coste: " + card.getCost();
    }

    private String compactMeta(Card card) {
        if (card instanceof CreatureCard creature) {
            return "Coste " + card.getCost() + "  |  " + creature.getPower() + "/" + creature.getToughness();
        }
        return "Coste " + card.getCost() + "  |  " + card.getType();
    }

    private ImageIcon loadCardImage(Card card, int width, int height) {
        URL resource = getClass().getClassLoader().getResource(card.getImagePath());
        if (resource == null) {
            return null;
        }

        ImageIcon icon = new ImageIcon(resource);
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new Main().setVisible(true);
        });
    }

    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setPaint(new GradientPaint(0, 0, new Color(4, 10, 18), getWidth(), getHeight(), new Color(32, 20, 9)));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}

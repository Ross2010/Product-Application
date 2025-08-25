import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop; // Import for opening links in a browser
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import javax.swing.BoxLayout; // Import for the new BoxLayout manager
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SlideShow extends JFrame {

    // Declare instance variables
    private JPanel slidePane;
    private JPanel textPane;
    private CardLayout card;
    private CardLayout cardText;

    // --- Inner Class for Slides ---
    // This inner class is a data structure to hold all the information for a single slide
    private static class Slide {
        final String description;
        final String imagePath;
        final String url;

        Slide(String description, String imagePath, String url) {
            // HTML is added here once
            this.description = "<html><body><font size='5'>" + description + "</body></html>";
            // Image tag is built once
            this.imagePath = "<html><body><img width= '700' height='400' src='" + getClass().getResource(imagePath) + "'</body></html>";
            this.url = url;
        }
    }

    // --- Slide Content List ---
    // A List of Slide objects
    private final List<Slide> slides = Arrays.asList(
        new Slide(
            "A Four Seasons Resort, Lanai City, Hawai",
            "/resources/Slide1.jpg",
            "https://www.fourseasons.com/sensei/"
        ),
        new Slide(
            "Camp Sarika at Amangiri, Canyon Point, UT",
            "/resources/Slide2.jpg",
            "https://www.aman.com/resorts/amangiri"
        ),
        new Slide(
            "Cuyo Archipelago Resort, Philippines ",
            "/resources/Slide3.jpg",
            "https://www.aman.com/resorts/amanpulo"
        ),
        new Slide(
            "Amanvari Resort, Mexico ",
            "/resources/Slide4.jpg",
            "https://www.aman.com/resorts/amanvari"
        ),
        new Slide(
            "Aman Kyoto, Japan",
            "/resources/Slide5.jpg",
            "https://www.aman.com/resorts/aman-kyoto"
        )
    );

    /**
     * Create the application.
     */
    public SlideShow() throws HeadlessException {
        initComponent();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initComponent() {
        // --- Refactored Variable Declarations ---
        // Variables are now declared and initialized on the same line for brevity.
        card = new CardLayout();
        cardText = new CardLayout();
        slidePane = new JPanel(card);
        textPane = new JPanel(cardText); 
        textPane.setBackground(new Color(0, 225, 225)); // background color is set for the text pane.
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Panels are initialized with their layouts directly.
        JButton btnPrev = new JButton("Previous");
        JButton btnNext = new JButton("Next");
        
        // --- Frame Setup ---
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Top 5 Wellness Destinations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // A for-each loop simplifies adding slides, replacing the old numeric for loop
        for (Slide slide : slides) {
            JLabel lblSlide = new JLabel();
            lblSlide.setText(slide.imagePath);
            
            // --- Adding MouseListener for Clickable Images ---
            lblSlide.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(slide.url));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace(); // Print stack trace if an error occurs
                    }
                }
            });
            slidePane.add(lblSlide, slide.description);

            JLabel lblTextArea = new JLabel();
            lblTextArea.setText(slide.description);
            textPane.add(lblTextArea, slide.description);
        }

        // Anonymous inner classes for ActionListeners are replaced with cleaner lambda expressions.
        btnPrev.addActionListener(e -> goPrevious());
        buttonPane.add(btnPrev);

        btnNext.addActionListener(e -> goNext());
        buttonPane.add(btnNext);
        
        // --- Fix for Layout Bug ---
        // A new JPanel, southPane, is created to hold both the text pane and the button pane.
        // BoxLayout is used to stack the components vertically.
        JPanel southPane = new JPanel();
        southPane.setLayout(new BoxLayout(southPane, BoxLayout.Y_AXIS));
        southPane.add(textPane);
        southPane.add(buttonPane);

        getContentPane().setLayout(new BorderLayout(10, 50));
        getContentPane().add(slidePane, BorderLayout.CENTER);
        getContentPane().add(southPane, BorderLayout.SOUTH); // Add the new southPane to the frame
    }

    /**
     * Previous Button Functionality
     */
    private void goPrevious() {
        card.previous(slidePane);
        cardText.previous(textPane);
    }

    /**
     * Next Button Functionality
     */
    private void goNext() {
        card.next(slidePane);
        cardText.next(textPane);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SlideShow ss = new SlideShow();
            ss.setVisible(true);
        });
    }
}
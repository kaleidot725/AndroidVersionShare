import androidx.compose.ui.awt.ComposePanel
import com.github.weisj.darklaf.LafManager
import com.github.weisj.darklaf.theme.DarculaTheme
import ui.app.App
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main() = SwingUtilities.invokeLater {
    LafManager.install(DarculaTheme())
    JFrame().also { window ->
        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        window.title = "VersionShareApp"
        window.setSize(800, 600)
        window.isVisible = true

        val composePanel = ComposePanel()
        window.contentPane.add(composePanel, BorderLayout.CENTER)
        composePanel.setContent { App() }
    }
}
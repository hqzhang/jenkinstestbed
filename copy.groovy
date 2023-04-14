import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
def sourcePath = Paths.get("srcfile")
def destinationPath = Paths.get("destfile")
Files.copy(sourcePath, destinationPath,StandardCopyOption.REPLACE_EXISTING)

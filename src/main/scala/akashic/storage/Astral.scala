package akashic.storage

import java.nio.file.{FileAlreadyExistsException, Files, Path}

// Astral is where everything is given birth and die
case class Astral(root: Path) {
  def alloc: Path = {
    val newPath = root.resolve(strings.random(32))
    try {
      Files.createDirectory(newPath)
    } catch {
      case e: FileAlreadyExistsException => alloc
      case e: Throwable => throw e
    }
  }

  def dispose(path: Path) {
    val newPath = root.resolve(strings.random(32))
    try {
      // no need to be atomic because if the trash remains in the tree
      // next compaction has a chance to find it out.
      Files.move(path, newPath)
    } catch {
      case e: FileAlreadyExistsException => dispose(path)
      case e: Throwable => throw e
    }
    files.purgeDirectory(newPath)
  }
}

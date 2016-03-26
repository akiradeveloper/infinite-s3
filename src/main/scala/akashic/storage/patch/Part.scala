package akashic.storage.patch

import java.nio.file.Path

import akashic.storage.backend.NodePath

object Part {
  def apply(path: NodePath) = new Part(Data.Pure(path))
}
case class Part(unwrap: Data.Pure) {
  def id = unwrap.name.toInt
}

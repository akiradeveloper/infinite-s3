package akasha.patch

case class Tree(root: Path) {
  def bucketPath(name: String): Path = root.resolve(name)
  def findBucket(name: String): Option[Bucket] = {
    val path = bucketPath(name)
    if (Files.exists(path) && Bucket(path).committed) {
      Some(Bucket(path))
    } else {
      None
    }
  }
  def listBuckets: Seq[Bucket] = {
    Files.children(root).map(Bucket(_)).filter(_.committed)
  }
}

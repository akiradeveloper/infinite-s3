package akashic.storage.patch

import java.nio.file.Path

import akashic.storage.backend.NodePath
import akashic.storage.caching.{CacheMap, Cache}
import akashic.storage.service.Meta.t
import akashic.storage.service.{Acl, Meta}

case class Version(key: Key, root: NodePath) extends Patch {
  val data = Data.Pure(root.resolve("data"))
  val meta = Meta.makeCache(root.resolve("meta"))
  val acl = Acl.makeCache(root.resolve("acl"))
  // [spec] All objects (including all object versions and delete markers)
  // in the bucket must be deleted before the bucket itself can be deleted.
  val deletable = false
}

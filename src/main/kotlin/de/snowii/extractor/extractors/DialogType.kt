package de.snowii.extractor.extractors

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import de.snowii.extractor.Extractor
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.server.MinecraftServer

class DialogType : Extractor.Extractor {
    override fun fileName(): String {
        return "dialog_type.json"
    }

    override fun extract(server: MinecraftServer): JsonElement {
        val json = JsonObject()
        val registry = BuiltInRegistries.DIALOG_TYPE

        for (codec in registry) {
            val key = registry.getKey(codec) ?: continue
            val entry = JsonObject()
            entry.addProperty("id", registry.getId(codec))

            val fields = JsonArray()
            val seen = LinkedHashSet<String>()
            codec.keys(JsonOps.INSTANCE)
                .map { it.asString }
                .filter { seen.add(it) }
                .forEach { fields.add(it) }
            entry.add("fields", fields)

            json.add(key.toString(), entry)
        }

        return json
    }
}

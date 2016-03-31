package com.thed.zephyr.cloud.rest.util.json.serializer;

import com.thed.zephyr.cloud.rest.model.Defect;
import com.thed.zephyr.cloud.rest.model.Execution;
import com.thed.zephyr.cloud.rest.model.enam.ExecutionFieldId;
import com.thed.zephyr.cloud.rest.model.enam.ExecutionStatusFieldId;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Created by aliakseimatsarski on 3/30/16.
 */
public class ExecutionSerializer extends JsonSerializer<Execution> {
    @Override
    public void serialize(Execution execution, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        jgen.writeStringField(ExecutionFieldId.ID.id, execution.id);
        jgen.writeNumberField(ExecutionFieldId.ISSUE_ID.id, execution.issueId);
        jgen.writeStringField(ExecutionFieldId.COMMENT.id, execution.comment != null ? execution.comment : "");

        //serialize Defects
        jgen.writeArrayFieldStart(ExecutionFieldId.DEFECTS.id);
        for(Defect defect:execution.defects) {
            jgen.writeString(defect.id.toString());
        }
        jgen.writeEndArray();

        //serialize Status
        jgen.writeObjectFieldStart(ExecutionFieldId.STATUS.id);
        jgen.writeNumberField(ExecutionStatusFieldId.ID.id, execution.status.id);
        jgen.writeEndObject();

        jgen.writeEndObject();
    }
}

/*
 * Copyright Gunnar Morling.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package dev.morling.quarkus.pdfextract;

import org.graalvm.nativeimage.Feature;

import com.oracle.svm.core.annotate.AutomaticFeature;
import com.oracle.svm.hosted.jni.JNIRuntimeAccess;

import sun.java2d.DefaultDisposerRecord;

@AutomaticFeature
class JNIRegistrationFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        try {
            JNIRuntimeAccess.register(
                    DefaultDisposerRecord.class.getDeclaredMethod("invokeNativeDispose", long.class, long.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}

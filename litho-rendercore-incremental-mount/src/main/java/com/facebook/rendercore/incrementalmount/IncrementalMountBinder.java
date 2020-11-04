/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.rendercore.incrementalmount;

import android.content.Context;
import androidx.annotation.Nullable;
import com.facebook.rendercore.RenderUnit;

public class IncrementalMountBinder implements RenderUnit.Binder<RenderUnit, Object> {

  private IncrementalMountExtensionInput input;
  private boolean isUpdating = false;

  void updateInput(IncrementalMountExtensionInput input) {
    this.input = input;
  }

  @Override
  public boolean shouldUpdate(
      final RenderUnit currentValue,
      final RenderUnit newValue,
      final @Nullable Object currentLayoutData,
      final @Nullable Object nextLayoutData) {
    isUpdating = true;
    return true;
  }

  @Override
  public void bind(
      final Context context,
      final Object content,
      final RenderUnit renderUnit,
      final @Nullable Object layoutData) {
    if (!isUpdating) {
      return;
    }

    isUpdating = false;
    IncrementalMountExtension.onBindMountItem(input, renderUnit.getId(), content);
  }

  @Override
  public void unbind(
      final Context context,
      final Object o,
      final RenderUnit lithoRenderUnit,
      final @Nullable Object layoutData) {}
}

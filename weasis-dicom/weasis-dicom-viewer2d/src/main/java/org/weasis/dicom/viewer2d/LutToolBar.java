/*
 * Copyright (c) 2009-2020 Weasis Team and other contributors.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0, or the Apache
 * License, Version 2.0 which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package org.weasis.dicom.viewer2d;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import org.weasis.core.api.gui.util.ActionState;
import org.weasis.core.api.gui.util.ActionW;
import org.weasis.core.api.gui.util.ComboItemListener;
import org.weasis.core.api.gui.util.DropButtonIcon;
import org.weasis.core.api.gui.util.DropDownButton;
import org.weasis.core.api.gui.util.GroupPopup;
import org.weasis.core.api.gui.util.ToggleButtonListener;
import org.weasis.core.api.util.ResourceUtil;
import org.weasis.core.api.util.ResourceUtil.ActionIcon;
import org.weasis.core.ui.editor.image.ImageViewerEventManager;
import org.weasis.core.ui.util.WtoolBar;
import org.weasis.dicom.codec.DicomImageElement;

public class LutToolBar extends WtoolBar {

  public LutToolBar(final ImageViewerEventManager<DicomImageElement> eventManager, int index) {
    super(Messages.getString("LutToolBar.lookupbar"), index);
    if (eventManager == null) {
      throw new IllegalArgumentException("EventManager cannot be null");
    }

    GroupPopup menu = null;
    ActionState presetAction = eventManager.getAction(ActionW.PRESET);
    if (presetAction instanceof ComboItemListener<?> comboListener) {
      menu = comboListener.createGroupRadioMenu();
    }

    final DropDownButton presetButton =
        new DropDownButton(ActionW.WINLEVEL.cmd(), buildWLIcon(), menu) {
          @Override
          protected JPopupMenu getPopupMenu() {
            JPopupMenu menu =
                (getMenuModel() == null) ? new JPopupMenu() : getMenuModel().createJPopupMenu();
            menu.setInvoker(this);
            return menu;
          }
        };

    presetButton.setToolTipText(Messages.getString("LutToolBar.presets"));
    add(presetButton);
    if (presetAction != null) {
      presetAction.registerActionState(presetButton);
    }

    GroupPopup menuLut = null;
    ActionState lutAction = eventManager.getAction(ActionW.LUT);
    if (lutAction instanceof ComboItemListener<?> comboListener) {
      menuLut = comboListener.createGroupRadioMenu();
    }

    final DropDownButton lutButton =
        new DropDownButton(ActionW.LUT.cmd(), buildLutIcon(), menuLut) {
          @Override
          protected JPopupMenu getPopupMenu() {
            JPopupMenu menu =
                (getMenuModel() == null) ? new JPopupMenu() : getMenuModel().createJPopupMenu();
            menu.setInvoker(this);
            return menu;
          }
        };

    lutButton.setToolTipText(Messages.getString("LutToolBar.lustSelection"));
    add(lutButton);
    if (lutAction != null) {
      lutAction.registerActionState(lutButton);
    }

    final JToggleButton invertButton = new JToggleButton();
    invertButton.setToolTipText(ActionW.INVERT_LUT.getTitle());
    invertButton.setIcon(ResourceUtil.getToolBarIcon(ActionIcon.INVERSE_LUT));
    ActionState invlutAction = eventManager.getAction(ActionW.INVERT_LUT);
    if (invlutAction instanceof ToggleButtonListener comboListener) {
      comboListener.registerActionState(invertButton);
    }
    add(invertButton);
  }

  private Icon buildLutIcon() {
    return DropButtonIcon.createDropButtonIcon(ResourceUtil.getToolBarIcon(ActionIcon.LUT));
  }

  private Icon buildWLIcon() {
    return DropButtonIcon.createDropButtonIcon(
        ResourceUtil.getToolBarIcon(ActionIcon.WINDOW_LEVEL));
  }
}
